package org.example.application;

import org.example.api.exception.AlreadyRegisterException;
import org.example.core.RoleRepository;
import org.example.core.user.User;
import org.example.core.user.UserRepository;
import org.example.core.userCredential.UserCredential;
import org.example.core.userCredential.UserCredentialRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.example.api.exception.AuthenticationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthServiceTest {
    // credential に ハッシュ化されたパスワードが渡されているかテスト(userCredentail の フィールド passwordHash の値で検証する)

    // すでに登録されているユーザであればエラーを返す
    // PasswordHash や User のインスタンス化せずにエラーを即座に返せていることを確認する

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserCredentialRepository userCredentialRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    // Mock の初期化をここに記述する
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(this.roleRepository.findRoleByName(any())).thenReturn(new Role(2,"USER"));
    }

    @Test
    void registerUser_hash_password_success(){
        // ① テストに必要なダミー変数などを設定
        // ② テストしたいロジックの実行
        // ③ 実行された変数をキャプチャして検証
        String encodedPassword = "%hashedPassword";
        // 以下①
        Integer userRoleId = this.roleRepository.findRoleByName("USER").getId();
        User user = new User(userRoleId,"testName","test@example.com");
        when(userRepository.save(any())).thenReturn(user);

        // PasswordEncoder は値を受け取ったら特定のハッシュパスワードを返す設定
        when(passwordEncoder.encode(any())).thenReturn(encodedPassword);

        // userRegisterParam のダミーを作成
        UserRegisterParam userRegisterParam = new UserRegisterParam(user.getName(),user.getEmail(),"rawPassword");

        // 以下②
        // authService の registerUser を呼び出す
        authService.registerUser(userRegisterParam);

        // 以下③
        // 適切な引数が渡されたか検証する [ArgumentCaptor を利用する]
        ArgumentCaptor<UserCredential> credentialCaptor = ArgumentCaptor.forClass(UserCredential.class);
        verify(userCredentialRepository).save(credentialCaptor.capture());
        // キャプチャした値を検証する
        UserCredential captured = credentialCaptor.getValue();
        // パスワードが生ではなく、PasswordEncoder が生成したハッシュ値になっているか
        assertEquals(encodedPassword, captured.getPasswordHash(), "保存されるパスワードはハッシュ化されている必要があります");
    }

    @Test
    void registerNotUniqueUser_fail() {
        Integer userRoleId = this.roleRepository.findRoleByName("USER").getId();
        User user = new User(userRoleId,"testName","test@example.com");
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        UserRegisterParam userRegisterParam = new UserRegisterParam(user.getName(),user.getEmail(),"rawPassword");
        assertThrows(AlreadyRegisterException.class, () -> {
            authService.registerUser(userRegisterParam);
        });
        verify(userRepository, never()).save(any());
        verify(userCredentialRepository, never()).save(any());
    }

    @Test
    void loginUser_success() {
        Integer userRoleId = this.roleRepository.findRoleByName("USER").getId();
        User user = new User(userRoleId,"testName","test@example.com");
        UserCredential userCredential = new UserCredential(1,"passwordHash");
        UserLoginParam userLoginParam = new UserLoginParam(user.getEmail(),"passwordHash");
        Optional<User> optDummyUser = Optional.of(user);
        Optional<UserCredential> optUserCredential = Optional.of(userCredential);

        when(userRepository.findUserByEmail(any())).thenReturn(optDummyUser);
        when(userCredentialRepository.get(any())).thenReturn(optUserCredential);
        when(passwordEncoder.matches(any(),any())).thenReturn(true);

        User loginedUser = authService.loginUser(userLoginParam);
        Assertions.assertEquals(loginedUser.getName(),user.getName());
        Assertions.assertEquals(loginedUser.getEmail(),user.getEmail());
    }

    @Test
    void updatePassword_success() {
     // updatePasswordParam 用意する
     // credentialRepository から userId に対応した credential 取得
     // 取得したパスワードハッシュと, updatePasswordParam の currentPassword を比較
     // userCredentialRepository.update がよばれることを確認
        UpdatePasswordParamRaw updatePasswordParamRaw = new UpdatePasswordParamRaw(1,"currentPassword","newPasswordRaw");
        UserCredential dummyCredential = new UserCredential(1,"currentPasswordHash");
        Optional<UserCredential> optDummyCredential = Optional.of(dummyCredential);

        when(userCredentialRepository.get(any())).thenReturn(optDummyCredential);
        when(passwordEncoder.matches(any(),any())).thenReturn(true);
        doNothing().when(userCredentialRepository).update(any(),any());
        authService.updatePassword(updatePasswordParamRaw);
        verify(userCredentialRepository,times(1)).update(any(),any());
    }

    @Test
    void deleteUser_success() {
        DeleteParam deleteParam = new DeleteParam(1,"currentPassword");
        UserCredential userCredential = new UserCredential(1,"passwordHash");
        Optional<UserCredential> optionalUserCredential = Optional.of(userCredential);
        when(this.userCredentialRepository.get(any())).thenReturn(optionalUserCredential);
        when(this.passwordEncoder.matches(any(),any())).thenReturn(true);
        doNothing().when(this.userCredentialRepository).delete(any());
        authService.deleteUser(deleteParam);
        verify(this.userCredentialRepository,times(1)).delete(any());
        verify(this.userRepository,times(1)).delete(any());
    }

    @Test
    void updatePassword_wrongPassword_fail() {
        // credentialRepository.get と update, passwordEncoder は mock する
        // user とを生成
        UserCredential currentCredential = new UserCredential(1,"passwordHash");
        Optional<UserCredential> optCurrentCredential = Optional.of(currentCredential);

        when(passwordEncoder.matches(any(),any())).thenReturn(false);
        when(this.userCredentialRepository.get(any())).thenReturn(optCurrentCredential);

        UpdatePasswordParamRaw updatePasswordParamRaw = new UpdatePasswordParamRaw(1,"currentPassword","newPasswordRaw");
        Exception e = Assertions.assertThrows(AuthenticationException.class,()->{
            authService.updatePassword(updatePasswordParamRaw);
        });

        Assertions.assertEquals("パスワードが不正です",e.getMessage());
    }

    @Test
    void loginUser_userNotFound_fail() {
        Integer userRoleId = this.roleRepository.findRoleByName("USER").getId();
        User user = new User(userRoleId,"testName","test@example.com");
        Exception e = Assertions.assertThrows(AuthenticationException.class,()->{authService.loginUser(new UserLoginParam(user.getEmail(),"passwordHash"));});
        Assertions.assertEquals("ユーザーが見つかりません",e.getMessage());
    }

    @Test
    void loginUser_credentialNotFound_fail() {
        Integer userRoleId = this.roleRepository.findRoleByName("USER").getId();
        User user = new User(userRoleId,"testName","test@example.com");
        Optional<User> optDummyUser = Optional.of(user);
        when(userRepository.findUserByEmail(any())).thenReturn(optDummyUser);

        Exception e = Assertions.assertThrows(AuthenticationException.class,()->{
            authService.loginUser(new UserLoginParam(user.getEmail(),"passwordHash"));
        });

        Assertions.assertEquals("認証情報が登録されていません",e.getMessage());
    }

    @Test
    void updatePassword_userNotFound_fail() {
        UserCredential userCredential = new UserCredential(1,"passwordHash");
        Optional<UserCredential> optUserCredential = Optional.of(userCredential);
        UpdatePasswordParamRaw dummyUpdatePasswordParamParamRaw = new UpdatePasswordParamRaw(1,"currentPassword","newPasswordRaw");

        UserCredential dummyCredential = new UserCredential(1,"passwordHash");
        Exception e = Assertions.assertThrows(AuthenticationException.class,()->{
            authService.updatePassword(dummyUpdatePasswordParamParamRaw);
        });
        Assertions.assertEquals("該当ユーザーがいません",e.getMessage());
    }

    @Test
    void loginUser_passwordWrong_fail() {
        Integer userRoleId = this.roleRepository.findRoleByName("USER").getId();
        User user = new User(userRoleId,"testName","test@example.com");
        UserCredential userCredential = new UserCredential(1,"passwordHash");
        UserLoginParam userLoginParam = new UserLoginParam(user.getEmail(),"wrongPasswordHash");
        Optional<User> optDummyUser = Optional.of(user);
        Optional<UserCredential> optUserCredential = Optional.of(userCredential);

        when(userRepository.findUserByEmail(any())).thenReturn(optDummyUser);
        when(userCredentialRepository.get(any())).thenReturn(optUserCredential);
        when(passwordEncoder.matches(any(),any())).thenReturn(false);

        Exception e = Assertions.assertThrows(AuthenticationException.class,()->{
            authService.loginUser(userLoginParam);
        });

        Assertions.assertEquals("認証エラー",e.getMessage());
    }


    @Test
    void deleteUser_userNotFound_fail() {
        UserCredential userCredential = new UserCredential(1,"passwordHash");
        Optional<UserCredential> optUserCredential = Optional.of(userCredential);
        DeleteParam deleteParam = new DeleteParam(1,"currentPassword");

        Exception e = Assertions.assertThrows(AuthenticationException.class,()->{
            authService.deleteUser(deleteParam);
        });
        Assertions.assertEquals("該当ユーザーがいません",e.getMessage());
    }

    @Test
    void deleteUser_passwordWrong_fail() {
        Integer userRoleId = this.roleRepository.findRoleByName("USER").getId();
        User user = new User(userRoleId,"testName","test@example.com");
        UserCredential userCredential = new UserCredential(1,"passwordHash");
        DeleteParam deleteParam = new DeleteParam(1,"wrongPasswordHash");
        Optional<User> optDummyUser = Optional.of(user);
        Optional<UserCredential> optUserCredential = Optional.of(userCredential);

        when(userRepository.findUserByEmail(any())).thenReturn(optDummyUser);
        when(userCredentialRepository.get(any())).thenReturn(optUserCredential);
        when(passwordEncoder.matches(any(),any())).thenReturn(false);

        Exception e = Assertions.assertThrows(AuthenticationException.class,()->{
            authService.deleteUser(deleteParam);
        });

        Assertions.assertEquals("パスワードが不正です",e.getMessage());
    }

}
