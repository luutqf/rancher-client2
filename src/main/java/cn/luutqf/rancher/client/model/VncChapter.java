package cn.luutqf.rancher.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

import static cn.luutqf.rancher.client.constant.Constants.VNC_TYPE;

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

    {
        this.bodyType = VNC_TYPE;
        this.containerType = "docker:";
    }

    private String password;

    private List<String> ports;



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
