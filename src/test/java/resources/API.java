package resources;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;

public class API {
    public final static String BASE_URL = "https://stellarburgers.nomoreparties.site";
    public final static String USER_LOGIN = "/api/auth/login";
    public final static String USER_DELETE = "/api/auth/user";
    public final static String USER_CREATE = "/api/auth/register";
    public final static String USER_LOGOUT = "/api/auth/logout";


    // базовые API запросы
    private static Response userCreate(String email, String password, String name) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(String.format("{\"email\": \"%s\", \"password\": \"%s\", \"name\": \"%s\"}", email, password, name))
                .post(BASE_URL + USER_CREATE);
        return response;
    }

    private static Response userLogin(String email, String password) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password))
                .post(BASE_URL + USER_LOGIN);
        return response;
    }

    private static Response userDelete(String bearerToken) {
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", bearerToken)
                .delete(BASE_URL + USER_DELETE);
        return response;
    }

    private static Response userLogout(String refreshToken) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(String.format("{\"token\": \"%s\"}", refreshToken))
                .post(BASE_URL + USER_LOGOUT);
        return response;
    }

    // методы для тестов

    public static void logoutUser(String email, String password) {
        Response response = userLogin(email, password);
        boolean success = response.then().extract().path("success");
        if (success) {
            String refreshToken = response.then().extract().path("refreshToken");
            response = userLogout(refreshToken);
            response.then().statusCode(SC_OK);
        }
    }

    public static void createUserBeforeTest(String email, String password, String name) {
        Response response = userCreate(email, password, name);
        response.then().statusCode(SC_OK);
    }

    public static void deleteUserAfterTest(String email, String password) {
        Response response = userLogin(email, password);
        boolean success = response.then().extract().path("success");
        if (success) {
            String bearerToken = response.then().extract().path("accessToken");
            response = userDelete(bearerToken);
            response.then().statusCode(SC_ACCEPTED);
        }
    }
}
