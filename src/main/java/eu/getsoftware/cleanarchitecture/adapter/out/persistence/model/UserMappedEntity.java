package eu.getsoftware.cleanarchitecture.adapter.out.persistence.model;

import eu.getsoftware.cleanarchitecture.application.domain.model.user.IUserDomainEntity;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * DB-Mapping of DOMAIN Entity
 * Constructor will be filled manuell from UserDTO (dto from higher Level, but no IUser interface implementation here!)
 */

@Entity
@Table(name = "user", schema = "userschema") //eu: Every bounded context stores its entities in its own DB schema
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class UserMappedEntity implements IUserDomainEntity //(no, manuell fill from DTO!!!)
{
    @Id
    @Setter(AccessLevel.PROTECTED)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userId;
    
    @NonNull
    @Column
    private String name;
    
    @NonNull
    @Column
    private String password;

    @OneToOne
    @JoinColumn(name = "address_id", nullable = false)
    private AddressMappedEntity address;
    
    @Column
    @NonNull
    private LocalDateTime creationTime;

    @Override
    public void setInitValues(String name) {
        this.setName(name);
        this.setCreationTime(LocalDateTime.now());
    }
}
