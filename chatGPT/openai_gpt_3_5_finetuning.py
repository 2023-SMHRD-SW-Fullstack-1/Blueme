import openai
import json
from secrets_1 import api_key

openai.api_key = api_key

#생성된 데이터셋 파일을 openai 에 등록합니다.
response = openai.File.create(
  file=open("chat_format_data.jsonl", "rb"),
  purpose='fine-tune'
)
print("File ID:", response['id'])
print("File size:", response['bytes'])

# findTUned 모델 만들기
# openai.FineTuningJob.create(training_file="file-uSM1ptNkgVf67ZP1F6B6eaJf", model="gpt-3.5-turbo")

# List 10 fine-tuning jobs
# response = openai.FineTuningJob.list(limit=10)
# print(response)

# Retrieve the state of a fine-tune
# openai.FineTuningJob.retrieve("ft-abc123")

# response = openai.FineTuningJob.create(
#   training_file='file-17oPwB2NZbwxHK9ZXbgoQnYY',
#   model='gpt-3.5-turbo',
# )

# print("Job ID:", response['id'])
