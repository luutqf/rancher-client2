package cn.luutqf.rancher.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VncChapter extends Chapter {

    private final String bodyYype = "vnc-";

    private final String ContainerType = "docker:";

    private String password;

    private List<Integer> ports;

    private String targetPort;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VncChapter)) return false;
        if (!super.equals(o)) return false;
        VncChapter that = (VncChapter) o;
        return Objects.equals(getPassword(), that.getPassword()) &&
            Objects.equals(getPorts(), that.getPorts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPassword(), getPorts());
    }
}
