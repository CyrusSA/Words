package cyrussa.github.com.words.Models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class AccessTokenTest {
    private AccessToken accessToken;
    private LocalDateTime currentTime;

    @Before
    public void setup(){
        currentTime = LocalDateTime.now();
        accessToken = new AccessToken("Test", "Test", 100, () -> currentTime);
    }

    @Test
    public void IsActive_TrueIfLifeIsntOver(){
        LocalDateTime endTime = currentTime.plusSeconds(50);
        accessToken.setCurrentLocalDateTimeSupplier(() -> endTime);
        Assert.assertTrue(accessToken.isActive());
    }

    @Test
    public void IsActive_FalseIfExactlyLifeIsOver(){
        LocalDateTime endTime = currentTime.plusSeconds(100);
        accessToken.setCurrentLocalDateTimeSupplier(() -> endTime);
        Assert.assertFalse(accessToken.isActive());
    }

    @Test
    public void IsActive_FalseIfLifeIsOver(){
        LocalDateTime endTime = currentTime.plusSeconds(105);
        accessToken.setCurrentLocalDateTimeSupplier(() -> endTime);
        Assert.assertFalse(accessToken.isActive());
    }
}
