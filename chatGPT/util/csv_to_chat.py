import csv
import json

with open('musics_202309191639_2.txt', 'r', encoding='utf-8') as f:
    reader = csv.DictReader(f)
    with open('chat_format_data.jsonl', 'w', encoding='utf-8') as outfile:
        for row in reader:
            tags = row['tag']
            genre = row['genre1']
            music_id = int(row['music_id'])
            title = row['title']
            artist = row['artist']

            user_content = f'태그: {tags} / 장르: {genre} 에 맞는 음악을 추천해주세요.'
            #assistant_content = json.dumps({"music_id": music_id, "title": title, "aritst" : artist}, ensure_ascii = False)
            assistant_content = f'{title} - {artist} 노래를 추천합니다.'

            entry = [
                {"role": "system", "content": 'recommendation assistant'},
                {"role": "user", "content": user_content},
                {"role": "assistant", "content": assistant_content}
            ]

            # Write each entry on its own line.
            outfile.write(json.dumps({"messages" : entry}, ensure_ascii=False) + '\n')
