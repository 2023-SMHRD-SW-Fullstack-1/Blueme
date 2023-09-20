import csv

def remove_duplicates(csv_file):
    with open(csv_file, 'r', encoding='utf-8') as f:
        reader = csv.reader(f)
        all_data = []
        for row in reader:
            for item in row:
                all_data.extend(item.split(', '))

    unique_data = list(set(all_data))

    for item in unique_data:
        print(item, end=",")

remove_duplicates('musics_202309200841.csv')
