import authenticationStatic.CredentialsStaticService;
import authenticationStatic.Authentication;
import authenticationStatic.PermissionStaticService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class TestMockStatic {
    Authentication authentication = new Authentication();
    @Test
    public void verifLoginCorrecto(){

        boolean esValido = true;

        MockedStatic<CredentialsStaticService> staticCredencialesMock = Mockito.mockStatic(CredentialsStaticService.class);
        staticCredencialesMock.when(()-> CredentialsStaticService.isValidCredential("admin", "123456abc")).thenReturn(esValido);
        String permission = "CRUD";

        MockedStatic<PermissionStaticService> staticPermissionMock = Mockito.mockStatic(PermissionStaticService.class);
        staticPermissionMock.when(()-> PermissionStaticService.getPermission("admin")).thenReturn(permission);

        String expectResult = "user authenticated successfully with permission: ["+permission+"]";
        String actualResult = authentication.login("admin", "123456abc");
        Assertions.assertEquals(expectResult, actualResult, "Error");

        staticCredencialesMock.close();
        staticPermissionMock.close();
    }
    @Test
    public void verifLoginIncorrecto(){
        boolean esValido = false;
        MockedStatic<CredentialsStaticService> staticCredencialesMock = Mockito.mockStatic(CredentialsStaticService.class);
        staticCredencialesMock.when(()-> CredentialsStaticService.isValidCredential("admin", "123456789ABC")).thenReturn(esValido);

        String expectResult = "user or password incorrect";
        String actualResult = authentication.login("admin", "123456789ABC");
        Assertions.assertEquals(expectResult, actualResult, "Error");

        staticCredencialesMock.close();
    }

}
