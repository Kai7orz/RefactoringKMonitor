# 実装計画

## 現状分析

### 実装済み
| コンポーネント | 状態             | 備考 |
|--------------|----------------|------|
| User | ✅ 完了           | エンティティ |
| UserRepository | ✅ 完了           | interface + MyBatis実装 |
| UserMapper.xml | ✅ 完了           | namespaceが `io.spring` になっている |
| UserCredential | ✅ 完了           | エンティティ |
| UserCredentialRepository | ⚠️ interfaceのみ | 実装クラスなし |
| AuthService | ⚠️ 部分的         | registerUserのみ |
| UserRegisterParam / UserLoginParam | ✅ 完了           | |
| H2 + MyBatisテスト基盤 | ⚠️ 要修正         | DIとtypo問題あり |

### 未実装
- UserCredentialMapper + XML
- AuthService (loginUser, updatePassword, deleteUser, canWriteRecord)
- UserService
- Record関連全般
- profiles, follows, comments, user_likes, roles, categories

---

## フェーズ1: User認証基盤の完成

### 1.1 既存コードの修正
- [ ] `UserMapper.xml` の namespace を `org.example.infrastructure.mybatis.mapper.UserMapper` に修正
- [ ] `MyBatisUserRepositoryTest.java` の @Autowired 追加とtypo修正
- [ ] `DbTestBase` を継承するように修正

### 1.2 UserCredential 永続化層
- [ ] `UserCredentialMapper.java` 作成 (interface)
- [ ] `UserCredentialMapper.xml` 作成
- [ ] `UserCredentialRepository` 実装クラス作成
- [ ] `schema.sql` に `user_credential` テーブル追加
- [ ] テスト作成

### 1.3 AuthService 完成
- [ ] `loginUser(UserLoginParam)` 実装
- [ ] `updatePassword(String passwordHash)` 実装
- [ ] `deleteUser(String id, String password)` 実装
- [ ] 各メソッドのテスト作成

### 1.4 UserApi 完成
- [ ] `registerUser` エンドポイント実装
- [ ] `UserLogin` エンドポイント実装
- [ ] JWT or Session 認証方式決定・実装

---

## フェーズ2: Record機能

### 2.1 エンティティ
- [ ] `Record.java` 作成
- [ ] `RecordDetail.java` 作成
- [ ] `RecordParam.java` 作成

### 2.2 永続化層
- [ ] `RecordRepository` interface 作成
- [ ] `RecordMapper.java` 作成
- [ ] `RecordMapper.xml` 作成
- [ ] `RecordRepository` 実装クラス作成
- [ ] `schema.sql` に `records` テーブル追加

### 2.3 サービス層
- [ ] `RecordService.java` 実装
  - getAllRecordsByUserId
  - getRecordDetailById
  - createRecord
  - updateRecordById
  - deleteRecordById

### 2.4 API層
- [ ] `RecordApi.java` 実装
- [ ] 認可チェック (`AuthService.canWriteRecord`) 実装

---

## フェーズ3: ソーシャル機能

### 3.1 Comments
- [ ] `Comment.java` エンティティ
- [ ] `CommentRepository` + Mapper
- [ ] `CommentService`
- [ ] `CommentApi`

### 3.2 Likes
- [ ] `UserLike.java` エンティティ
- [ ] `UserLikeRepository` + Mapper
- [ ] Recordへのいいね機能

### 3.3 Follows
- [ ] `Follow.java` エンティティ
- [ ] `FollowRepository` + Mapper
- [ ] フォロー/フォロワー機能

---

## フェーズ4: 補助機能

### 4.1 Profiles
- [ ] `Profile.java` エンティティ
- [ ] プロフィール画像アップロード
- [ ] プロフィール編集

### 4.2 Roles
- [ ] `Role.java` エンティティ
- [ ] 権限管理機能

### 4.3 Categories
- [ ] `Category.java` エンティティ
- [ ] `RecordCategory.java` 中間テーブル
- [ ] カテゴリ検索機能

---

## 優先度

```
高 ←――――――――――――――――――――――――――→ 低

1.1 修正  →  1.2 Credential  →  1.3 Auth  →  1.4 API
                                              ↓
           2.x Record機能  ←―――――――――――――――――┘
                  ↓
           3.x ソーシャル機能
                  ↓
           4.x 補助機能
```

---

## 次のアクション

**今すぐ着手すべき:**
1. `UserMapper.xml` の namespace 修正
2. `MyBatisUserRepositoryTest.java` の修正
3. `UserCredentialMapper` の作成
