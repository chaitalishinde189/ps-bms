package com.ps.thm.model;

import com.ps.thm.enums.Format;
import com.ps.thm.enums.Language;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Table(value = "shows")
@NoArgsConstructor
@EqualsAndHashCode
public class ShowEntity {

    @PrimaryKey
    private ShowPrimaryKey key;
    @Column
    private Language language;
    @Column
    private Format format;

}
