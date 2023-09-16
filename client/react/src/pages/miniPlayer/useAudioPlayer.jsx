import { useState, useEffect } from 'react';
import { Howl } from 'howler';

export const useAudioPlayer = (url) => {
  const [sound, setSound] = useState(null);
  const [isPlaying, setIsPlaying] = useState(false);
  
  useEffect(() => {
    if (sound) sound.unload();

    const newSound = new Howl({
      src: [url],
      format: ["mpeg"],
      onload() {
        setIsPlaying(true);
        newSound.play();
      },
      onend() {
        // 여기에 다음 곡으로 넘어가는 로직 추가
      },
      onplay() {
        setIsPlaying(true);
      },
      onpause() {
        setIsPlaying(false);
      },
    });

    setSound(newSound);

    return () => {
      if (sound) sound.unload();
    };
  }, [url]);

  return { isPlaying, sound };
};