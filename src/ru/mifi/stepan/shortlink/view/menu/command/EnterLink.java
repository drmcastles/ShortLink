package ru.mifi.stepan.shortlink.view.menu.command;

import ru.mifi.stepan.shortlink.domain.ILinkService;
import ru.mifi.stepan.shortlink.domain.IUserService;
import ru.mifi.stepan.shortlink.domain.ServiceFabric;
import ru.mifi.stepan.shortlink.dto.LimitExpiredInfoDto;
import ru.mifi.stepan.shortlink.exception.LimitExpiredException;
import ru.mifi.stepan.shortlink.exception.LinkNotFoundException;
import ru.mifi.stepan.shortlink.view.menu.UserMenu;
import ru.mifi.stepan.shortlink.view.utils.ConsoleUtil;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Меню перехода по короткой ссылке
 */
public class EnterLink extends CommandMenu {

    private IUserService userService = ServiceFabric.getUserService();
    private ILinkService linkService = ServiceFabric.getLinkService();

    public EnterLink(UserMenu mainMenu) {
        super(mainMenu);
    }

    @Override
    protected String getCommandResult() {
        System.out.println("Введите короткую ссылку");
        String shortLink = ConsoleUtil.readFromConsole();
        String originalLink = null;
        try {
            originalLink = linkService.getOriginalLink(shortLink);
            Desktop.getDesktop().browse(new URI(originalLink));
            LimitExpiredInfoDto limitExpiredInfoDto = linkService.decreaseLimit(shortLink);
            return "Успешный переход, лимит - " +
                    limitExpiredInfoDto.getLimit() + ", действует до " +
                    limitExpiredInfoDto.getExpiredAt();
        } catch (IOException e) {
            return "Ошибка ввода вывода";
        } catch (URISyntaxException e) {
            return "Некорректно указана ссылка " + originalLink;
        } catch (LinkNotFoundException e) {
            return e.getMessage();
        }  catch (LimitExpiredException e) {
            return e.getMessage();
        }

    }
}
