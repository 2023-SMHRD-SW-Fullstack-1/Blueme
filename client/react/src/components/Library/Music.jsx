import React, { useState } from 'react';
// import - 이미지
import likeEmpty from '../img/likeEmpty.png';
import dummy from '../data/MusicDummy.json';

const Music = () => {
    return (
        <div className="flex flex-col items-center">
            {dummy.map((item) => {
                return (
                    <div key={item.id} className="flex flex-row items-center w-full p-2">
                        {/* 1. 앨범커버 */}
                        <img src={item.coverImage} className="w-[50px] h-auto rounded-lg" />
                        <div className="flex flex-col text-left ml-2">
                            {/* 2. 곡명 */}
                            <span className="text-lg font-bold">{item.title}</span>
                            {/* 3. 가수명 */}
                            <span className="text-sm">{item.artist}</span>
                        </div>
                        {/* 빈 공간을 채우기 위한 Flex Grow */}
                        <div className="flex-grow"></div>
                        {/* 4. 좋아요 버튼*/}
                        <img src={likeEmpty} className="w-[30px] h-auto rounded-lg align" />
                    </div>
                );
            })}
        </div>
    );
};

export default Music;
