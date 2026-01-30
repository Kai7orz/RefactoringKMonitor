```mermaid  
classDiagram
    User 
    User: -Integer id
    User: -String name
    User: -String email
    User: -Integer role_id
    User: -LocalDateTime created_at
    User: -LocalDateTime updated_at
    User: +updateUserInfo(String name,String email,Integer role_id)
    User: +changePassword(String passwordHash)

    UserRegisterParam
    UserRegisterParam: String name
    UserRegisterParam: String email
    UserRegisterParam: String rowPassword

    UserLoginParam
    UserLoginParam: String email
    UserLoginParam: String rowPassword

    UserService
    UserService: +updateUserPassword(String id)
    UserService: +deleteUser(String id,String password)
    
    UserApi
    UserApi: +registerUser(UserRegisterParam userRegisterParam)
    UserApi: +userLogin(UserLoginParam userLoginParam)
    
    UserCredential
    UserCredential: -userId
    UserCredential: -passwordHash
    UserCredential: +changePassword(String passwordHash)
    
    UserRepositoryInterface
    UserRepositoryInterface: +User findUserByEmail(String Email)
    UserRepositoryInterface: +List<User> findAllUsers()
    UserRepositoryInterface: +void deleteUserById(Integer userId)
    
    UserRepository
    UserRepository: -UserMapper userMapper
    UserRepository: +User findUserByEmail(String Email)
    UserRepository: +List<User> findAllUsers()
    UserRepository: +void deleteUserById(Integer userId)


    AuthService
    AuthService: -bool canRegisterUser(UserRegisterParam userRegisterParam)
    AuthService: +User registerUser(UserRegisterParam userRegisterParam)
    AuthService: +User loginUser(UserLoginParam userLoginParam)
    AuthService: +bool canWriteRecord(User user,Record record)

    PasswordEncoder
    PasswordEncoder: -hashPassword(String password)

    AuthService --> PasswordEncoder : password の hash 化を依頼する
    AuthService ..> UserRegisterParam
    AuthService ..> UserLoginParam : LoginParam 受け取る
    AuthService --> UserRepositoryInterface
    User -- UserCredential
    UserApi --> AuthService
    UserApi --> UserService
    UserRepository --|> UserRepositoryInterface
    UserService --> UserRepositoryInterface
    UserService ..> User : entityのメソッドを呼び出す
    UserService ..> UserCredential

    
```
