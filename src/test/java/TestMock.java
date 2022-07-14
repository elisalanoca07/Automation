import authentication.Authentication;
import authentication.CredentialsService;
import authentication.PermissionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestMock {
    Authentication authentication;

    CredentialsService credentialsServiceMock = Mockito.mock(CredentialsService.class);
    PermissionService permissionServiceMock = Mockito.mock(PermissionService.class);

    @BeforeEach
    void setup(){
        authentication = new Authentication();
    }

    @Test
    public void pruebaMsgCorrecto(){

        boolean esValido = true;
        Mockito.when(credentialsServiceMock.isValidCredential("Admin", "temporal123")).thenReturn(esValido);

        authentication.setCredentialsService(credentialsServiceMock);

        String permission = "CRUD";

        Mockito.when(permissionServiceMock.getPermission("Admin")).thenReturn(permission);
        authentication.setPermissionService(permissionServiceMock);

        String exceptedResult = "user authenticated successfully with permission: ["+permission+"]";
        String actualResult = authentication.login("Admin", "temporal123");

        Assertions.assertEquals(exceptedResult, actualResult, "error!");

        Mockito.verify(credentialsServiceMock).isValidCredential("Admin", "temporal123");
        Mockito.verify(permissionServiceMock).getPermission("Admin");
    }
    @Test
    public void pruebaMsgIncorrecto(){

        boolean esValido = false;
        Mockito.when(credentialsServiceMock.isValidCredential("admin", "aaaaabbbbb")).thenReturn(esValido);

        authentication.setCredentialsService(credentialsServiceMock);

        String expectedResult = "user or password incorrect";
        String actualResult = authentication.login("admin", "aaaaabbbbb");

        Assertions.assertEquals(expectedResult, actualResult, "error!");

        Mockito.verify(credentialsServiceMock).isValidCredential("admin", "aaaaabbbbb");

    }
}
