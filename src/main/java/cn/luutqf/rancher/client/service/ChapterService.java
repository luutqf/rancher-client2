package cn.luutqf.rancher.client.service;

import cn.luutqf.rancher.client.exception.RancherException;
import cn.luutqf.rancher.client.model.Chapter;
import io.rancher.base.TypeCollection;
import io.rancher.service.ProjectApi;
import io.rancher.type.Container;
import retrofit2.Response;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */
public interface ChapterService<T extends Chapter> extends ContainerService<T> {

    Optional<String> findUrl(String id);

    Optional<Container> find(String id);

    Boolean deleteChapter(T chapter);

    default String getContainerName(T t) {
        Logger log = Logger.getLogger(this.getClass().getName());
        try {
            Class<?> aClass;
            if (t.getClass() == Chapter.class) aClass = t.getClass();
            else aClass = t.getClass().getSuperclass();
            Field usernameF = aClass.getDeclaredField("username");
            Field imageF = aClass.getDeclaredField("image");
            Field chapterNameF = aClass.getDeclaredField("chapterName");
            imageF.setAccessible(true);
            usernameF.setAccessible(true);
            chapterNameF.setAccessible(true);
            return usernameF.get(t).toString() + "-" + chapterNameF.get(t).toString() + "-" + imageF.get(t).toString().substring(imageF.get(t).toString().indexOf("/") + 1);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.warning("Class Field の problem :" + toString());
            throw new RancherException(e.getMessage(), RancherException.CHAPTER_ERROR);
        }
    }

    default Optional<String> getId(Container container, T t, String project, ProjectApi api) {
        Response<Container> execute = createContainer(container, t, project, api);
        if (Objects.requireNonNull(execute).message().startsWith("Unprocessable")) {
            return getIdUnique(t, project, api);
        }
        if (Optional.ofNullable(execute.body()).isPresent())
            return Optional.ofNullable(execute.body().getId());
        return Optional.empty();
    }

    default Response<Container> createContainer(Container container, T t, String project, ProjectApi api) {
        container.setImageUuid(t.getContainerType() + t.getImage());
        container.setName(getContainerName(t));
        try {
            return api.createContainer(project, container).execute();
        } catch (IOException e) {
            throw new RancherException(e.getMessage(), RancherException.CREATE_CHAPTER_ERROR);
        }
    }

    default Optional<String> getIdUnique(T t, String project, ProjectApi api) {
        TypeCollection<Container> body;
        try {
            body = api.listContainers(project).execute().body();
        } catch (IOException e) {
            throw new RancherException(e.getMessage(), RancherException.LIST_CONTAINER_ERROR);
        }
        if (!Optional.ofNullable(body).isPresent()) {
            return Optional.empty();
        }
        for (Container c : body.getData()) {
            boolean name = c.getName().equals(getContainerName(t));
            if (name) {
                return Optional.ofNullable(c.getId());
            }
        }
        return Optional.empty();
    }

}
