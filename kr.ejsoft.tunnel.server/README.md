## Execute
```
keytool -genkey -alias serverkey -keyalg RSA -keypass changeit -storepass changeit -keystore server.keystore
keytool -genkey -alias clientkey -keyalg RSA -keypass changeit -storepass changeit -keystore client.keystore

키 저장소 비밀번호 입력:
새 비밀번호 다시 입력:
이름과 성을 입력하십시오.
  [Unknown]:  ...이름...
조직 단위 이름을 입력하십시오.
  [Unknown]:  ...조직 부서명...
조직 이름을 입력하십시오.
  [Unknown]:  ...조직 이름...
구/군/시 이름을 입력하십시오?
  [Unknown]:  ...주소 2...
시/도 이름을 입력하십시오.
  [Unknown]:  ...주소 1...
이 조직의 두 자리 국가 코드를 입력하십시오.
  [Unknown]:  ...국가코드...
CN="******", OU=******, O=******, L=******, ST=******, C=**이(가) 맞습니까?
  [아니오]:  Y

<serverkey>에 대한 키 비밀번호를 입력하십시오.
        (키 저장소 비밀번호와 동일한 경우 Enter 키를 누름):


keytool -export -alias serverkey -storepass changeit -file server.cer -keystore server.keystore
keytool -export -alias clientkey -storepass changeit -file client.cer -keystore client.keystore

keytool -import -v -trustcacerts -alias serverkey -file .\server.cer -keystore cacerts.keystore -keypass changeit -storepass changeit
keytool -import -v -trustcacerts -alias clientkey -file .\client.cer -keystore cacerts.keystore -keypass changeit -storepass changeit

```
