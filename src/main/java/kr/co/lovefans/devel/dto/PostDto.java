package kr.co.lovefans.devel.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PostDto {

    private Long sl_v_mi_seq;
    private String cp_title;
    private String cp_content;
    private String cp_tag;
    private String cp_link;
    private Date cp_regdt;

    public PostDto() {
    }
    public PostDto(Long sl_v_mi_seq, String cp_title, String cp_content, String cp_tag, String cp_link, Date cp_regdt) {
        this.sl_v_mi_seq = sl_v_mi_seq;
        this.cp_title = cp_title;
        this.cp_content = cp_content;
        this.cp_tag = cp_tag;
        this.cp_link = cp_link;
        this.cp_regdt = cp_regdt;
    }


}
