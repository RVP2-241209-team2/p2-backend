package  com.revature.shoply.registration.dto;
import com.revature.shoply.models.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegistrationResponseDTO {
    @Getter @Setter
    private UUID id;
    @Getter @Setter
    private String username;
    @Getter @Setter
    private String firstName;
    @Getter @Setter
    private String lastName;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String phoneNumber;
    @Getter @Setter
    private UserRole role;
}
