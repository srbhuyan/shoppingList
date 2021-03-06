package de.yannicklem.shoppinglist;

import de.yannicklem.shoppinglist.core.user.entity.SLAuthority;
import de.yannicklem.shoppinglist.core.user.entity.SLUser;
import de.yannicklem.shoppinglist.core.user.registration.entity.Confirmation;

import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;


public class TestUtils {

    public static SLUser completelyInitializedTestUser(String name) {

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SLAuthority(SLAuthority.USER));

        return new SLUser(name, "Test", "Mustermann", "trollinger", name + "@test.de", true,
                completelyInitializedConfirmation(), authorities);
    }


    public static Confirmation completelyInitializedConfirmation() {

        return new Confirmation(null, "safsafsa21e10smsad");
    }


    public static SLUser completelyInitializedTestAdmin(String name) {

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SLAuthority(SLAuthority.USER));
        authorities.add(new SLAuthority(SLAuthority.ADMIN));

        return new SLUser(name, "Test", "Mustermann", "trollinger", name + "@test.de", true,
                completelyInitializedConfirmation(), authorities);
    }
}
