package ru.mifi.stepan.shortlink.view.menu;

import ru.mifi.stepan.shortlink.domain.ServiceFabric;
import ru.mifi.stepan.shortlink.view.menu.command.EnterLink;
import ru.mifi.stepan.shortlink.view.menu.command.EnterMenu;
import ru.mifi.stepan.shortlink.view.menu.command.LinkCreateMenu;
import ru.mifi.stepan.shortlink.view.menu.option.MenuOption;
import java.util.ArrayList;
import java.util.List;

public class MainMenu extends AbstractMenu {

    private final IMenu SHOW_USER_MENU = new UserBrowser(this);

    private final IMenu USER_MENU = new UserMenu(this);
    private final IMenu LINK_CREATE_MENU = new LinkCreateMenu(USER_MENU);
    private final IMenu ENTER_MENU = new EnterMenu(USER_MENU, this);
    @Override
    protected List<MenuOption> getOption() {
        List<MenuOption> mainMenuOptions = new ArrayList<>();
        mainMenuOptions.add(new MenuOption("1","Вход", ENTER_MENU));
        mainMenuOptions.add(new MenuOption("2","Создание короткой ссылки", LINK_CREATE_MENU));
        mainMenuOptions.add(new MenuOption("3","Ввывести список всех пользователей", SHOW_USER_MENU));
        return mainMenuOptions;
    }

    @Override
    protected void postCreate() {
        ServiceFabric.getUserManagement().setUserId(null);
        super.postCreate();
    }

    @Override
    public String getName() {
        return "Основное меню";
    }
}
