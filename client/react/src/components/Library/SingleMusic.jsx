import React, { useState } from 'react';
// import - 이미지
import likeEmpty from '../../assets/img/likeEmpty.png';

const SingleMusic = ({ item }) => {
    return (
        <div className="flex flex-col items-center ml-2 mr-2">
            <div className="flex flex-row items-center w-full p-2">
                <img src={item.coverImage} className="w-[50px] h-auto rounded-lg" />
                <div className="flex flex-col text-left ml-2">
                    <span className="text-lg font-bold">{item.title}</span>
                    <span className="text-sm">{item.artist}</span>
                </div>
                <div className="flex-grow"></div>
                <img src={likeEmpty} className="w-[30px] h-auto rounded-lg align" />
            </div>
        </div>
    );
};

export default SingleMusic;
