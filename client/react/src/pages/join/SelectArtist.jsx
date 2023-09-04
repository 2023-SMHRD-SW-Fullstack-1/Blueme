import React from 'react';
import { Link } from 'react-router-dom';

const SelectArtist = () => {
    return (
        <div className="flex flex-col justify-center items-center bg-custom-blue text-custom-white overflow-auto flex-grow">
            <h3 class="font-bold text-4xl sm:text-2xl md:text-2xl sm:mb-10 pb-10 pt-10">당신이 좋아하는 가수는?</h3>
            <div class="grid grid-cols-3 sm:grid-cols-3 md:grid-cols- gap-x-6 gap-y-1">
                {Array(12)
                    .fill()
                    .map((_, i) => (
                        <button key={i} class="flex flex-col items-center space-y-1 mb-10">
                            <img
                                src={`https://randomuser.me/api/portraits/men/${i + 1}.jpg`}
                                alt=""
                                class="rounded-lg w-[100px] h-auto object-cover"
                            />
                            {/* <h5 class="font-semibold">Bernard</h5> */}
                        </button>
                    ))}
            </div>
            <div className="w-full h-auto rounded-lg p-4">
                <Link to="/Main" className="mb-10">
                    <button
                        className="
            mt-5
            w-full
            px-3 h-10 relative 
            bg-[#221a38]  
            rounded-lg border border-soild border-[#fdfdfd]
            text-custom-white"
                    >
                        수정하기
                    </button>
                </Link>
            </div>
        </div>
    );
};

export default SelectArtist;
