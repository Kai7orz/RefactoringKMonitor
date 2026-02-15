package org.example.application;

import net.bytebuddy.asm.Advice;
import org.example.core.user.User;
import org.example.core.user.UserRepository;
import org.example.core.userCredential.UserCredential;
import org.example.core.userCredential.UserCredentialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthServiceTest {
    // credential に ハッシュ化されたパスワードが渡されているかテスト(userCredentail の フィールド passwordHash の値で検証する)

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserCredentialRepository userCredentialRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    // Mock の初期化をここに記述する
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void registerUser_hash_password_successfully(){
        // ① テストに必要なダミー変数などを設定
        // ② テストしたいロジックの実行
        // ③ 実行された変数をキャプチャして検証
        String encodedPassword = "%hashedPassword";
        // 以下①
        User dummyUser = new User("testName","test@example.com");
        when(userRepository.save(any())).thenReturn(dummyUser);

        // PasswordEncoder は値を受け取ったら特定のハッシュパスワードを返す設定
        when(passwordEncoder.encode(any())).thenReturn(encodedPassword);

        // userRegisterParam のダミーを作成
        UserRegisterParam dummyUserRegisterParam = new UserRegisterParam("test","test@example.com","rowPassword");

        // 以下②
        // authService の registerUser を呼び出す
        authService.registerUser(dummyUserRegisterParam);

        // 以下③
        // 適切な引数が渡されたか検証する [ArgumentCaptor を利用する]
        ArgumentCaptor<UserCredential> credentialCaptor = ArgumentCaptor.forClass(UserCredential.class);
        verify(userCredentialRepository).save(credentialCaptor.capture());
        // キャプチャした値を検証する
        UserCredential captured = credentialCaptor.getValue();
        // パスワードが生ではなく、PasswordEncoder が生成したハッシュ値になっているか
        assertEquals(encodedPassword, captured.getPasswordHash(), "保存されるパスワードはハッシュ化されている必要があります");
    }
}
