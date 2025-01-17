package ru.mifi.stepan.shortlink.view.menu;

import ru.mifi.stepan.shortlink.domain.ServiceFabric;
import ru.mifi.stepan.shortlink.dto.NotificationDto;
import ru.mifi.stepan.shortlink.view.menu.command.EnterLink;
import ru.mifi.stepan.shortlink.view.menu.command.LinkCreateMenu;
import ru.mifi.stepan.shortlink.view.menu.command.LinkDeleteMenu;
import ru.mifi.stepan.shortlink.view.menu.command.LinkEditMenu;
import ru.mifi.stepan.shortlink.view.menu.command.LinkShowMenu;
import ru.mifi.stepan.shortlink.view.menu.option.MenuOption;

import java.util.ArrayList;
import java.util.List;

public class UserMenu extends AbstractMenu {

    private static final String NOTIFICATION_PATTERN = "%s\t%s";
    private final IMenu LINK_DELETE_MENU = new LinkDeleteMenu(this);
    private final IMenu LINK_EDIT_MENU = new LinkEditMenu(this);
    private final IMenu LINK_SHOW_MENU = new LinkShowMenu(this);
    private final IMenu parent;
    private final IMenu LINK_CREATE_MENU = new LinkCreateMenu(this);
    private final IMenu ENTER_LINK_MENU = new EnterLink(this);


    public UserMenu(IMenu parent) {
        this.parent = parent;
    }

    @Override
    protected void printNotification() {
        super.printNotification();
        ServiceFabric.getCleanManagerService().monitor();
        List<NotificationDto> notifications = ServiceFabric.getNotificationService().readNotifications();
        if (notifications == null || notifications.isEmpty()) {
            return;
        }
        notifications.forEach(notificationDto ->
                System.out.println(String.format(NOTIFICATION_PATTERN, notificationDto.getLink(), notificationDto.getNotificationType()))
        );
    }

    @Override
    protected List<MenuOption> getOption() {
        List<MenuOption> mainMenuOptions = new ArrayList<>();
        mainMenuOptions.add(new MenuOption("1", "Просмотр ссылок", LINK_SHOW_MENU));
        mainMenuOptions.add(new MenuOption("2", "Редактирование ссылок", LINK_EDIT_MENU));
        mainMenuOptions.add(new MenuOption("3", "Удалить ссылку", LINK_DELETE_MENU));
        mainMenuOptions.add(new MenuOption("4", "Создание короткой ссылки", LINK_CREATE_MENU));
        mainMenuOptions.add(new MenuOption("5","Перейти по короткой ссылке", ENTER_LINK_MENU));
        mainMenuOptions.add(new MenuOption("6", "Назад", parent));
        return mainMenuOptions;
    }

    @Override
    public String getName() {
        return "Пользовательское меню";
    }
}
