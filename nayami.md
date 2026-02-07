## 悩みポイント
- User サービスに deleteUser(String passwordHash) メソッドを実装したいが User サービスで完結させるには
PasswordEncoder クラスを注入しないといけない．だが，セキュリティに関するものは AuthServiceだけにとどめたいので
AuthService 以外で PasswordEncoder DI したくない．
  - UserService -> AuthService への依存は OK な設計とする．（逆はNG）
- 例外設計の方法がわからない
  - Controller まで伝播した例外を @RestControllerAdvice で処理する
    - Service, Controller レイヤーにはあまりtry-catch 書かないことで可読性を向上させる. 
    - ExceptionHandler を用いて，@Controller がついているメソッドのエラー処理を集約する