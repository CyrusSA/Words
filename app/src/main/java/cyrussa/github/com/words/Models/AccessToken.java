package cyrussa.github.com.words.Models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Supplier;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Getter
public class AccessToken {
    private final String token;
    private final String type;
    private final int lifeInSeconds;
    private final LocalDateTime createdOn;

    // For testing
    @Setter
    private Supplier<LocalDateTime> currentLocalDateTimeSupplier;

    public AccessToken(String token, String type, int lifeInSeconds){
        currentLocalDateTimeSupplier = LocalDateTime::now;
        this.token = token;
        this.type = type;
        this.lifeInSeconds = lifeInSeconds;
        this.createdOn = currentLocalDateTimeSupplier.get();
    }

    // For testing
    public AccessToken(String token, String type, int lifeInSeconds, Supplier<LocalDateTime> currentLocalDateTimeSupplier){
        this.currentLocalDateTimeSupplier = currentLocalDateTimeSupplier;
        this.token = token;
        this.type = type;
        this.lifeInSeconds = lifeInSeconds;
        this.createdOn = currentLocalDateTimeSupplier.get();
    }

    public boolean isActive(){
        return Duration.between(createdOn, currentLocalDateTimeSupplier.get()).toMillis()/1000 < lifeInSeconds;
    }
}
