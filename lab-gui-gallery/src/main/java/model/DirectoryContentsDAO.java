package model;

import java.util.Collection;

public class DirectoryContentsDAO {

    private Collection<ThumbnailDTO> thumbnails;
    private Collection<String> directories;

    public static DirectoryContents convertTo(DirectoryContentsDAO dto) {
        var thumbnails = dto.thumbnails.stream().map(x -> ThumbnailDTO.convertTo(x)).toList();
        return new DirectoryContents(thumbnails, dto.directories);
    }
}
