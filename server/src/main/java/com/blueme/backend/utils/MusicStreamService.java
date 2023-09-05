package com.blueme.backend.utils;

import java.io.InputStream;

public interface MusicStreamService {
  InputStream loadMusicStream(Long id);
}
