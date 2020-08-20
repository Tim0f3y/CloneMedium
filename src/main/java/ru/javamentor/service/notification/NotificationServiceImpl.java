package ru.javamentor.service.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javamentor.dao.notification.NotificationDao;
import ru.javamentor.dto.NotificationDto;
import ru.javamentor.dto.TopicDto;
import ru.javamentor.model.Notification;
import ru.javamentor.model.Topic;

import java.util.ArrayList;
import java.util.List;
/**
 * Реализация интерфейса NotificationService
 *
 * @version 1.0
 * @author Java Mentor
 */
@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    private NotificationDao notificationDao;

    @Autowired
    public NotificationServiceImpl(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }

    /**
     * метод для получения всех уведомлений
     *
     * @return List уведомлений
     */
    @Override
    public List<Notification> getAllNotes() {
        try {
            List<Notification> notifications = notificationDao.getAllNotes();
            log.debug("IN getAllNotes: {} notifications", notifications.size());
            return notifications;
        } catch (Exception e) {
            log.error("Exception while getAllNotes in service");
            throw new RuntimeException();
        }
    }
    /**
     * метод для получения уведомлений отдельного юзера
     *
     * @return List уведомлений по ID юзера
     */
    @Override
    public List<Notification> getAllNotesById(Long id) {
        try {
            List<Notification> notifications = notificationDao.getAllNotesById(id);
            log.debug("IN getAllNotesById: {} notifications", notifications.size());
            return notifications;
        } catch (Exception e) {
            log.error("Exception while getAllNotesById in service");
            throw new RuntimeException();
        }
    }

    /**
     * метод для получения уведомления по уникальному Id
     *
     * @return Notification - объект уведомления
     */
    @Override
    public Notification getById(Long id) {
        try {
            Notification notification = notificationDao.getOne(id);
            log.debug("IN getById: return notification.id: {} and notification.title: {}", id, notification.getTitle());
            return notification;
        } catch (Exception e) {
            log.error("Exception while getById in service notification.id: {}", id);
            throw new RuntimeException();
        }
    }

    /**
     * метод для получения уведомления по названию
     *
     * @param title - наименование уведомления
     * @return Notification - объект уведомления
     */
    @Override
    public Notification getByTitle(String title) {
        try {
            Notification notification = notificationDao.getByTitle(title);
            log.debug("IN getByTitle: return notification by title: {}", title);
            return notification;
        } catch (Exception e) {
            log.error("Exception while getByTitle in service by notification.title: {}", title);
            throw new RuntimeException();
        }
    }

    /**
     * метод для обновления уведомления
     *
     * @param notification - объект обновленнного уведомления
     * @return boolean - удалось обновить уведомление или нет
     */
    @Override
    public boolean updateNotification(Notification notification) {
        if (notification != null) {
            notificationDao.updateNotification(notification);
            log.debug("IN updateNotification: notification.id: {} and notification.title: {} was updated",
                    notification.getId(), notification.getTitle());
            return true;
        } else {
            log.debug("IN updateNotification: notification.id: {} and notification.title: {} not found",
                    notification.getId(), notification.getTitle());
        }
        return false;
    }

    /**
     * метод для добавления уведомления
     *
     * @param notification - объект добавляемого уведомления
     * @return boolean - удалось добавить уведомление или нет
     */
    @Transactional
    @Override
    public boolean addNotification(Notification notification) {
        if (notification != null) {
            notificationDao.addNotification(notification);
            log.debug("addNotification: notification {} was add", notification.getTitle());
            return true;
        }
        log.debug("addNotification: notification {} was not add", notification.getTitle());
        return false;
    }

    /**
     * метод для удаления уведомления
     *
     * @param notification - объект удаляемого уведомления
     * @return boolean - удалось удалить уведомление или нет
     */
    @Override
    public boolean deleteNotification(Notification notification) {
        try {
            notificationDao.deleteNotification(notification);
            log.debug("IN deleteNotification: notification.id: {} and notification.title: {} was deleted",
                    notification.getId(), notification.getTitle());
            return true;
        } catch (Exception e) {
            log.error("Exception while deleteNotification in service: notification.id: {} and notification.title: {}",
                    notification.getId(), notification.getTitle());
            throw new RuntimeException();
        }
    }

    /**
     * Метод получения списка NotificationsDto из списка Notifications
     *
     * @param notifList - лист нотификаций
     * @return - список Notification DTO
     */
    @Override
    public List<NotificationDto> getNotificationDtoListByNotifList(List<Notification> notifList) {
        List<NotificationDto> notificationDtoList = new ArrayList<>();
        notifList.forEach(notification -> notificationDtoList.add(new NotificationDto(notification)));
        return notificationDtoList;
    }

    @Override
    public boolean isExist(Long notifId){
        return notificationDao.isExist(notifId);
    }

}
