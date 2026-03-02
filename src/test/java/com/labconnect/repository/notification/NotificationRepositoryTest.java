package com.labconnect.repository.notification;

import com.labconnect.models.notification.Notification;
import com.labconnect.repository.notification.NotificationRepository;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class NotificationRepositoryTest {

    @Test
    void testRepositoryMock() {
        NotificationRepository repo = mock(NotificationRepository.class);

        Notification n = new Notification();
        n.setNotificationId(1L);

        when(repo.findById(1L)).thenReturn(Optional.of(n));

        Optional<Notification> result = repo.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getNotificationId());
    }

    @Test
    void testFindByUserId() {
        NotificationRepository repo = mock(NotificationRepository.class);

        Notification n1 = new Notification();
        Notification n2 = new Notification();

        // FIX: Use the method that exists in the interface
        when(repo.findByUser_UserId(10L)).thenReturn(Arrays.asList(n1, n2));

        var result = repo.findByUser_UserId(10L);

        assertEquals(2, result.size());
        verify(repo, times(1)).findByUser_UserId(10L);
    }
}
