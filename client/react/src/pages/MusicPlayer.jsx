import React, { useState, useEffect } from 'react';
import { Howl } from 'howler';

// import - 음악
import song1 from '../assets/audios/heavyrain.mp3';
import song2 from '../assets/audios/barking.mp3';
import song3 from '../assets/audios/wind.mp3';

// import - 플레이어 아이콘
import { ReactComponent as Prev } from '../assets/img/musicPlayer/backward.svg';
import { ReactComponent as Next } from '../assets/img/musicPlayer/forward.svg';
import { ReactComponent as Play } from '../assets/img/musicPlayer/play.svg';
import { ReactComponent as Pause } from '../assets/img/musicPlayer/pause.svg';
import { ReactComponent as Rotate } from '../assets/img/musicPlayer/rotate.svg';

// import - 이미지
import likeEmpty from '../assets/img/likeEmpty.png';
import likeFull from '../assets/img/likeFull.png';
import scroll from '../assets/img/musicPlayer/scrollDown.png';

// import 더미데이터
import dummy from '../dummy/MusicDummy.json';

const audioClips = [
    { sound: song1, label: 'Heavy Rain' },
    { sound: song2, label: 'barking' },
    { sound: song3, label: 'wind' },
];

const MusicPlayer = () => {
    const [soundIndex, setSoundIndex] = useState(0);
    const [sound, setSound] = useState(null);
    const [isPlaying, setIsPlaying] = useState(false);

    useEffect(() => {
        createSound(soundIndex);
        return () => {
            if (sound) {
                sound.unload();
            }
        };
    }, []);

    const createSound = (index) => {
        if (sound) {
            sound.unload();
        }

        const newSound = new Howl({
            src: [audioClips[index].sound],
            html5: true,
        });

        setSound(newSound);
        setSoundIndex(index);

        return newSound;
    };

    const playSound = () => {
        if (!isPlaying) {
            sound.play();
            setIsPlaying(true);
        }
    };

    const pauseSound = () => {
        if (isPlaying) {
            sound.pause();
            setIsPlaying(false);
        }
    };

    const nextTrack = () => {
        let newIndex = (soundIndex + 1) % audioClips.length;
        let nextTrack = createSound(newIndex);

        if (isPlaying) {
            nextTrack.play();
            setIsPlaying(true);
        }

        setSound(nextTrack);
        setSoundIndex(newIndex);
    };

    const prevTrack = () => {
        let newIndex = (soundIndex - 1 + audioClips.length) % audioClips.length;
        let prevTrack = createSound(newIndex);

        if (isPlaying) {
            prevTrack.play();
            setIsPlaying(true);
        }

        setSound(prevTrack);
        setSoundIndex(newIndex);
    };

    console.log();
    return (
        <div className="flex flex-col items-center justify-center bg-custom-blue text-custom-white h-full">
            <p>앨범명 : {dummy[0].album}</p>
            <div>
                <img src={dummy[0].coverImage} className="w-[300px] h-auto rounded-lg pb-[20px]" />
                <p className="text-2xl pb-[10px]">곡제목 : {audioClips[soundIndex].label}</p>
                <p>아티스트명: {dummy[0].artist}</p>
            </div>
            <div className="flex flex-row items-center justify-center items-center gap-5 py-10">
                <Rotate className="w-[30px] h-auto" />
                <Prev className="w-[40px] h-auto" onClick={prevTrack} />
                {isPlaying ? (
                    <Pause className="w-[50px] h-auto" onClick={pauseSound} />
                ) : (
                    <Play className="w-[50px] h-auto" onClick={playSound} />
                )}
                <Next className="w-[40px] h-auto" onClick={nextTrack} />
                <div className="ml-auto">
                    <img className="w-[30px] h-auto " src={likeEmpty} />
                </div>
            </div>
            <img src={scroll} className="w-[40px] h-auto fixed bottom-[10%]" />
        </div>
    );
};

export default MusicPlayer;
