## 悩みポイント
- User サービスに deleteUser(String passwordHash) メソッドを実装したいが User サービスで完結させるには
PasswordEncoder クラスを注入しないといけない．だが，セキュリティに関するものは AuthServiceだけにとどめたいので
AuthService 以外で PasswordEncoder DI したくない．
  - UserService -> AuthService への依存は OK な設計とする．（逆はNG）