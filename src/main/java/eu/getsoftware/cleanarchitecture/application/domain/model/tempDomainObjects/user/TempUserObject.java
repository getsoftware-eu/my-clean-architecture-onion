package eu.getsoftware.cleanarchitecture.application.domain.model.tempDomainObjects.user;

import eu.getsoftware.cleanarchitecture.application.domain.model.tempDomainObjects.TempAddressValueObject;
import eu.getsoftware.cleanarchitecture.application.domain.model.user.IUserDomain;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Eugen:
 * Layer of "business rules" + Entity (data):
 * 
 * Package PRIVACY: should be not public OUTSIDE PACKAGE
 * 
 */
@Getter
@Setter
class TempUserObject implements IUserDomain
{
    String name;
    String password;
    LocalDateTime creationTime;
    TempAddressValueObject address;

    @Override
    public void setInitValues(String name) {
        this.name = name;
        this.creationTime = LocalDateTime.now();
    }
}