import React, { useEffect, useState, useRef } from "react";
import axios from "axios";

const AddTheme = () => {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [musicIds, setMusicIds] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [musicList, setMusicList] = useState([]);
  const [searchList, setSearchList] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 30;
  const [isImgUploaded, setIsImgUploaded] = useState(false);
  const [imgFile, setImgFile] = useState("");
  const imgRef = useRef();
  // 음악 데이터 가져오기
  useEffect(() => {
    const fetchMusicData = async () => {
      try {
        const response = await axios.get(
          `http://172.30.1.27:8104/music/page?page=${currentPage}&size=${itemsPerPage}`
        );
        setMusicList(response.data);
      } catch (error) {
        console.error("음악 데이터 로딩 중 오류가 발생했습니다:", error);
      }
    };
    fetchMusicData();
  }, [currentPage]);

  // 체크박스 클릭 핸들러
  const handleCheckboxClick = (id) => {
    if (musicIds.includes(id)) {
      setMusicIds(musicIds.filter((musicId) => musicId !== id));
    } else {
      setMusicIds([...musicIds, id]);
    }
  };

  const handleTitleChange = (e) => {
    setTitle(e.target.value);
  };

  const handleContentChange = (e) => {
    setContent(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    /*
    try {
      await axios.post("http://172.30.1.27:8104/theme/register", {
        title,
        content,
        musicIds,
      });
      alert("테마가 성공적으로 등록되었습니다.");
    } catch (error) {
      console.error("테마 등록 중 오류가 발생했습니다:", error);
    }

    try {
      const formData = new FormData();
      formData.append("theme_img_file", imgRef.current.files[0]);

      await axios.post(
        "http://172.30.1.27:8104/theme/register/image",
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
    } catch (error) {
      console.error("이미지 업로드 중 오류가 발생했습니다:", error);
    }*/

    try {
      const formData = new FormData();
      formData.append("theme_img_file", imgRef.current.files[0]);
      formData.append(
        "requestDto",
        JSON.stringify({ title, content, musicIds })
      );

      await axios.post("http://172.30.1.27:8104/theme/register", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });

      // 요청 성공 후의 로직...
    } catch (error) {
      console.error("테마 등록 중 오류가 발생했습니다:", error);
    }

    setTitle("");
    setContent("");
    setMusicIds([]);
  };

  // 음악검색
  const handleSearchChange = (event) => {
    setSearchTerm(event.target.value);
  };

  const handleSearchSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.get(
        `http://172.30.1.27:8104/music/search?keyword=${searchTerm}`
      );
      setSearchList(response.data);
      console.log(response.data);
    } catch (error) {
      console.error("음악 검색 중 오류가 발생했습니다:", error);
    }
  };

  // 이미지 업로드 검사(파일 업로드 안할시 인증하기 막음)
  const handleIsImgUploaded = () => {
    setIsImgUploaded(true);
  };

  // 사진 미리보기
  const saveImgFile = () => {
    handleIsImgUploaded();
    const file = imgRef.current.files[0];
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onloadend = () => {
      setImgFile(reader.result);
      setIsImgUploaded(true);
    };
  };

  return (
    <div className="relative flex flex-col flex-1 overflow-y-auto overflow-x-hidden">
      <main>
        <div className="px-4 sm:px-6 lg:px-8 py-14 w-full max-w-9xl mx-auto my-6">
          {/* Cards */}
          <h1 className="text-2xl md:text-4xl lg:text-4xl font-bold mb-4 text-[#1E293B]">
            테마 등록
          </h1>
          <form>
            <div className="mb-4">
              <div className="flex flex-col items-center">
                <img
                  className="w-32 h-32 object-cover rounded-full shadow-md"
                  src={imgFile ? imgFile : `previewimg.png`}
                  alt=""
                />
                <label
                  className="mt-4 text-lg font-semibold text-gray-700"
                  htmlFor="profileImg"
                >
                  테마사진
                </label>
                <input
                  className="mt-2"
                  type="file"
                  accept="image/*"
                  id="profileImg"
                  onChange={saveImgFile}
                  ref={imgRef}
                  name="theme_img_file"
                />
              </div>

              <label htmlFor="title" className="block mb-2">
                제목:
              </label>
              <input
                type="text"
                id="title"
                value={title}
                onChange={handleTitleChange}
                required
                className="border p-2 rounded w-full"
              />
            </div>

            {/* 내용 */}
            <div className="mb-4">
              <label htmlFor="content" className="block mb-2">
                내용:
              </label>
              <textarea
                id="content"
                value={content}
                onChange={handleContentChange}
                required
                className="border p-2 rounded w-full"
              />
            </div>

            <input
              type="submit"
              value="등록"
              className="py-1 px-3 bg-[#1E293B] hover:bg-blue700 text-white font-bold rounded cursor-pointer"
              onClick={handleSubmit}
            />
          </form>
          <div>
            <h1 className="text-2xl md:text-4xl lg:text-2xl font-bold mb-4 text-[#1E293B]">
              음악 선택
            </h1>
            {/* 음악검색 */}
            <form onSubmit={handleSearchSubmit} className="flex items-center">
              <input
                type="text"
                value={searchTerm}
                onChange={handleSearchChange}
                placeholder="음악 검색..."
                className="py-2 px-4 border border-gray-300 rounded-l-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
              />
              <button
                type="submit"
                className="bg-[#1E293B] hover:bg-blue-700 text-white py-2 px-4 rounded-r-md transition-colors duration-300 ease-in-out"
              >
                검색
              </button>
            </form>
          </div>

          {/* 음악 리스트 테이블 */}
          <div className="overflow-x-auto">
            {/* 검색리스트 테이블 */}
            {searchList && searchList.length > 0 && (
              <table className="w-full table-auto border-collapse mb-4">
                <thead>
                  <tr>
                    <th className="border p-2"></th>
                    <th className="border p-2">제목</th>
                    <th className="border p-2">아티스트</th>
                    <th className="border p-2">앨범명</th>
                    <th className="border p-2">장르</th>
                    <th className="border p-2">태그</th>
                  </tr>
                </thead>
                <tbody>
                  {searchList &&
                    searchList &&
                    searchList.map((music) => (
                      <tr key={music.id}>
                        {/* 체크박스 */}
                        <td className="border p-2">
                          <input
                            type="checkbox"
                            checked={musicIds.includes(music.id)}
                            onChange={() => handleCheckboxClick(music.id)}
                          />
                        </td>
                        <td className="border p-2">{music.title}</td>
                        <td className="border p-2">{music.artist}</td>
                        <td className="border p-2">{music.album}</td>
                        <td className="border p-2">{music.genre1}</td>
                        <td className="border p-2">{music.tag}</td>
                      </tr>
                    ))}
                </tbody>
              </table>
            )}

            <table className="w-full table-auto border-collapse mb-4">
              <thead>
                <tr>
                  <th className="border p-2"></th> {/* 체크박스용 열 */}
                  <th className="border p-2">제목</th>
                  <th className="border p-2">아티스트</th>
                  <th className="border p-2">앨범명</th>
                  <th className="border p-2">장르</th>
                  <th className="border p-2">태그</th>
                </tr>
              </thead>

              <tbody>
                {musicList &&
                  musicList.content &&
                  musicList.content.map((music) => (
                    <tr key={music.id}>
                      {/* 체크박스 */}
                      <td className="border p-2">
                        <input
                          type="checkbox"
                          checked={musicIds.includes(music.id)}
                          onChange={() => handleCheckboxClick(music.id)}
                        />
                      </td>
                      <td className="border p-2">{music.title}</td>
                      <td className="border p-2">{music.artist}</td>
                      <td className="border p-2">{music.album}</td>
                      <td className="border p-2">{music.genre1}</td>
                      <td className="border p-2">{music.tag}</td>
                    </tr>
                  ))}
              </tbody>
            </table>
            <div className="w-full flex justify-center">
              {/* 페이지네이션 버튼들 */}
              {currentPage > 1 && (
                <button
                  onClick={() => setCurrentPage(currentPage - 1)}
                  className="py-2 px-2 bg-[#1E293B] hover:bg-blue700 text-white font-bold rounded cursor-pointer mr-4 text-sm/[5px]"
                >
                  &lt;&lt;
                </button>
              )}

              <button
                onClick={() => setCurrentPage(currentPage + 1)}
                className="py-2 px-2 bg-[#1E293B] hover:bg-blue700 text-white font-bold rounded cursor-pointer text-sm/[5px]"
              >
                &gt;&gt;
              </button>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
};

export default AddTheme;
