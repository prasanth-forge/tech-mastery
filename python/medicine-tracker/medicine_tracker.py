from openpyxl import load_workbook
from datetime import date
from dotenv import load_dotenv
import os
import requests

load_dotenv()


def get_entries(worksheet):
    entries = []
    for row in worksheet.iter_rows(min_row=2, values_only=True):
        if any(cell is not None for cell in row):
            entries.append(
                {
                    "serial": row[1],
                    "ordered": row[2],
                    "started": row[4],
                    "ended": row[5],
                }
            )
    return entries


def find_current_entry(entries):
    for entry in entries:
        if entry["started"].date() <= date.today() <= entry["ended"].date():
            return entry


def find_batch_end(entries, current):
    batch_number = int(current["serial"])
    batch_entries = [e for e in entries if int(e["serial"]) == batch_number]
    batch_end = max(batch_entries, key=lambda e: e["ended"])
    return batch_end["ended"]


def check_alerts(entries, current):
    batch_end = find_batch_end(entries, current).date()
    days_remaining = (batch_end - date.today()).days

    if days_remaining == 7:
        send_telegram("⚠️ 7 days left — time to order next batch")
    elif days_remaining == 4:
        send_telegram("🚨 4 days left — order urgently")
    else:
        send_telegram(f"✅ {days_remaining} days remaining until batch ends")

    next_batch_num = int(current["serial"]) + 1
    next_batch = [e for e in entries if e["serial"] == next_batch_num + 0.1]

    if next_batch and next_batch[0]["ordered"] is None:
        send_telegram("📋 Next batch not ordered yet")


def send_telegram(message):
    token = os.environ.get("TELEGRAM_TOKEN")
    chat_id = os.environ.get("TELEGRAM_CHAT_ID")
    try:
        response = requests.get(
            f"https://api.telegram.org/bot{token}/sendMessage?chat_id={chat_id}&text={message}"
        )
        if response.status_code != 200:
            response.raise_for_status()
    except Exception:
        print("Unable to notify you over telegram")


if __name__ == "__main__":
    wb = load_workbook("assets/Medicine Tracker.xlsx")
    ws = wb["Schedule Of Medication"]
    entries = get_entries(ws)
    current = find_current_entry(entries)
    check_alerts(entries, current)
