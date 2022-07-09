package com.ps.thm.helper;

import com.ps.thm.dto.Show;
import com.ps.thm.model.ShowEntity;
import com.ps.thm.model.ShowPrimaryKey;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DataMapper {
    public ShowEntity mapToShowEntity(Show show) {
        ShowPrimaryKey key = new ShowPrimaryKey();
        ShowEntity showEntity = new ShowEntity();
        BeanUtils.copyProperties(show, key);
        BeanUtils.copyProperties(show, showEntity);
        key.setId(UUID.randomUUID());
        showEntity.setKey(key);
        return showEntity;
    }

    public Show mapToShow(ShowEntity showEntity) {
        Show show = new Show();
        BeanUtils.copyProperties(showEntity.getKey(), show);
        BeanUtils.copyProperties(showEntity, show);
        return show;
    }

}
