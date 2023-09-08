import React, { useEffect, useState } from "react";
import axios from "axios";

const AddTheme = () => {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [musicIds, setMusicIds] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [musicList, setMusicList] = useState([]);
  const [searchList, setSearchList] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 50;

  // 음악 데이터 가져오기
  useEffect(() => {
    const fetchMusicData = async () => {
      try {
        const response = await axios.get(
          `http://172.30.1.27:8104/music/page?paging=${currentPage}&size=${itemsPerPage}`
        );
        setMusicList(response.data);
        console.log(response);
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

    try {
      await axios.post("/api/your-endpoint", { title, content, musicIds });
      alert("테마가 성공적으로 등록되었습니다.");
    } catch (error) {
      console.error("테마 등록 중 오류가 발생했습니다:", error);
    }

    // 필요에 따라 상태 초기화
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

  return (
    <div className="relative flex flex-col flex-1 overflow-y-auto overflow-x-hidden">
      <main>
        <div className="px-4 sm:px-6 lg:px-8 py-14 w-full max-w-9xl mx-auto my-6">
          {/* Cards */}
          <h1 className="text-2xl md:text-4xl lg:text-4xl font-bold mb-4 text-[#1E293B]">
            테마 등록
          </h1>
          <form onSubmit={handleSubmit}>
            <div className="mb-4">
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
                    <th className="border p-2"></th> {/* 체크박스용 열 */}
                    {/* 필요한 만큼의 열을 추가하세요. */}
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
                        {/* 필요한 만큼의 열을 추가하세요. */}
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
                  {/* 필요한 만큼의 열을 추가하세요. */}
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
                      {/* 필요한 만큼의 열을 추가하세요. */}
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
