package smartcontact.util;

public class UserSignInResponse {
    private String message;
    private Long uId; // Ensure this matches the expected type

    // Constructors, getters, and setters
    public UserSignInResponse(String message, Long uId) {
        this.message = message;
        this.uId = uId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUId() {
        return uId;
    }

    public void setUId(Long uId) {
        this.uId = uId;
    }
}
