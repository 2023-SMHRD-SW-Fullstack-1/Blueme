import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

const Theme = () => {
  const [themes, setThemes] = useState([]);

  useEffect(() => {
    const fetchThemes = async () => {
      try {
        const response = await axios.get("/theme/themelists");
        setThemes(response.data);
      } catch (error) {
        console.error(`Error: ${error}`);
      }
    };

    fetchThemes();
  }, []);

  return (
    <div className=" h-full flex flex-col justify-center items-center bg-custom-blue text-custom-white overflow-auto flex-grow">
      <h3 class="font-bold text-4xl sm:text-2xl md:text-2xl sm:mb-10">당신이 선호하는 테마는?</h3>
      <div class="grid grid-cols-2 sm:grid-cols-2 md:grid-cols-2 text-custom-black">
        {themes.map((theme, i) => {
          const themeImage = theme?.image || `/theme/themelists//${i + 1}`;
          const themeName = theme?.title;
          const themeId = theme?.id || i + 1;

          const handleButtonClick = () => {
            localStorage.setItem("themeImage", themeImage);
            localStorage.setItem("themeName", themeName);

            localStorage.setItem("themeId", themeId);
          };

          return (
            <button
              key={i}
              class="w-full h-full flex flex-col items-center justify-center p-8 bg-white rounded-lg shadow-md"
              onClick={handleButtonClick}
            >
              <img src={themeImage} alt="" class="rounded-lg w-[150px] h-[150px] object-cover" />
              <h5 class="font-semibold">{themeName}</h5>
            </button>
          );
        })}
      </div>
      <div className="w-full h-auto rounded-lg p-4">
        <Link to="/Playlist" className="mb-10">
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

export default Theme;
