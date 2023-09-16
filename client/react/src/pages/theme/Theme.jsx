/*
작성자: 신지훈
날짜: 2023-09-15
설명: 테마 버그 세부 디자인 수정   
*/

import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

const Theme = () => {
  const [themes, setThemes] = useState([]);

  useEffect(() => {
    const fetchThemes = async () => {
      try {
        const response = await axios.get("/theme/themelists");
        console.log(response.data);
        setThemes(response.data);
      } catch (error) {
        console.error(`Error: ${error}`);
      }
    };

    fetchThemes();
  }, []);

  return (
    <div className="overflow-auto hide-scrollbar  min-h-screen mb-auto bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-byte text-custom-white font-semibold ">
      <p className="text-center pt-10 sm:pt-20 text-xl sm:text-3xl mt-10"></p>
      <div class="grid grid-cols-2 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-2 xl:px-[15%] gap-x-[1px] gap-y-[1px] mt-[10px] mb-[90px] lg:gap-x-[-111px] text-custom-black mx-auto max-w-screen-lg justify-items-center">
        {themes.map((theme, i) => {
          const themeImage = theme?.img;
          const themeName = theme?.title;
          const themeId = theme?.id || i + 1;

          const handleButtonClick = () => {
            localStorage.setItem("themeImage", themeImage);
            localStorage.setItem("themeName", themeName);

            localStorage.setItem("themeId", themeId);

            console.log("Stored in local storage:");
            console.log("Theme image:", localStorage.getItem("themeImage"));
          };

          return (
            <button key={i} class="flex flex-col rounded-xl shadow-md mt-4 mb-4" onClick={handleButtonClick}>
              <Link to="/ThemePlaylist" className="">
                <div class="flex flex-col items-center justify-center text-center text-custom-white">
                  <img
                    src={"data:image/;base64," + themeImage}
                    alt=""
                    class="rounded-lg w-[180px] h-[170px] object-cover mt-sm "
                  />
                  <h5 class="font-semibold text-base mt-3 w-[80%]">{themeName}</h5>
                </div>
              </Link>
            </button>
          );
        })}
      </div>
    </div>
  );
};

export default Theme;
