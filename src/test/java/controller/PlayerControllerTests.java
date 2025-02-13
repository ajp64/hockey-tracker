package controller;

import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import service.PlayerService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PlayerControllerTests {

    @Mock
    private PlayerService mockPlayerService;

    private PlayerController testSubject;

    @BeforeEach
    void setUp(){
        testSubject = new PlayerController(mockPlayerService);
    }

    @Test
    void testViewPlayers()
    {
        List<Player> expected = List.of();

        when(mockPlayerService.getPlayers()).thenReturn(expected);

        ResponseEntity<List<Player>> actual = testSubject.viewPlayers();

        assertThat(actual.getBody()).isEqualTo(expected);
    }

}
