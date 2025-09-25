package com.doyak.reflector.domain;

import java.util.ArrayList;
import java.util.List;

import com.doyak.reflector.domain.common.BaseEntity;
import com.doyak.reflector.domain.enums.Level;
import com.doyak.reflector.domain.enums.Site;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "posts")
public class Post extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long postId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Enumerated(EnumType.STRING)
	private Site site;
	
	private String title;
	private Level level;
	
	@Lob
	private String content;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("orderIndex ASC")
	private List<Block> blocks = new ArrayList<>();

	// 업데이트 
	public void update(Site site, Level level, String title, String content) {
        if (site != null) this.site = site;
        if (level != null) this.level = level;
        if (title != null) this.title = title;
        if (content != null) this.content = content;
    }
	
}
