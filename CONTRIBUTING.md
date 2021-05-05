# Contribution guide

## Requirements

- [JDK 11](https://openjdk.java.net/projects/jdk/11/) - (Java 11.09 is the preferred version);
- [EditorConfig](https://editorconfig.org/) - Download a plugin (if needed) [here](https://editorconfig.org/#download);
- [Maven](https://maven.apache.org/download.cgi) - A Maven script wrapper already exists in this project and doesn't need any installation (you use `mvnw` scripts with Maven commands);

## Configuration

### Maven

You can use the `ci_settings.xml` file as a configuration file to run some Maven commands.

Example of usage command locally: `mvnw clean install -s ci_settings.xml --batch-mode`

## CI/CD

This repository it's configured to use Gitlab pipelines as a way to build, test and publish its assets.

See `.gitlab-ci.yml`

### CI/CD Environments

The following environments variable need to be configured in the project settings:

- `REPO_USER` - User that have access in the Nexus repository manager.
- `REPO_PASS` - Password of the user.

See `ci_settings.xml`

## Security

### Database password

The password of the database is encrypted and configured in the `.properties` file, you can encrypt a new password using the following code snippet:

```java
import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricConfig;
import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricStringEncryptor;
import org.jasypt.encryption.StringEncryptor;

import static com.ulisesbocchio.jasyptspringboot.util.AsymmetricCryptography.KeyFormat.PEM;

public class PropertyEncryptor {
  public static void main(String[] args) {
    SimpleAsymmetricConfig config = new SimpleAsymmetricConfig();
    config.setKeyFormat(PEM);
    config.setPublicKey("-----BEGIN PUBLIC KEY-----\n" +
      "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArQfyGCvBOdgmDGU6ciGP\n" +
      "VNB6jHsMip0b0qOrPvVTSJ/x0offjKARogA2tjGjyr3rUtwg9woMBqv/iyENR0GB\n" +
      "nIUa0jkYsznCKeygcflnNa4mrVf7XKXLhSwtY+kCe3diPk+0QPfEsfF9/aK6pWBU\n" +
      "FcrE8P2k2sF/8mo8dFJU1t6zQGPspHkNAgR6MLU8SjPZxnMS6EG722MdYhvSYAKs\n" +
      "nu02Hozqb4jh/gaQ/E6NkvM3DkqIyIYsRH2smstIFEb9CCiTdiz/OsJKQLgGy/pq\n" +
      "IVKtai3lnUxAayEV45Z61rNTOusNJf+icGhZxjqhAeoWjMxOCVmVC2GKa9sisqBg\n" +
      "kQIDAQAB\n" +
      "-----END PUBLIC KEY-----\n");
    StringEncryptor encryptor = new SimpleAsymmetricStringEncryptor(config);
    String password = "123456";
    String encrypted = encryptor.encrypt(password);
    System.out.printf("Encrypted password: %s\n", encrypted);
  }
}
```
The decryption process uses the `private_key.pem` file in the resources folder.

## Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.0/maven-plugin/reference/htmlsingle/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.0/maven-plugin/reference/htmlsingle#build-image)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.4.0/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Jersey](https://docs.spring.io/spring-boot/docs/2.4.0/reference/htmlsingle/#boot-features-jersey)
* [External configuration](https://docs.spring.io/spring-boot/docs/1.0.1.RELEASE/reference/html/boot-features-external-config.html)
* [Jasypt Spring Boot](https://github.com/ulisesbocchio/jasypt-spring-boot)

## Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

## Tools

- [SQLDeveloper](https://www.oracle.com/br/tools/downloads/sqldev-v192-downloads.html) - Used to create the E-R Diagram, create and maintain Oracle objects such as table, index, etc;
- [Swagger Editor](https://editor.swagger.io/) - Used to see the [Open API Specification](docs/extrato_report.yaml) more easily;
- [Postman](https://www.postman.com/product/rest-client/) - Used to call the API without frontend;
