from openpyxl import load_workbook
from datetime import date


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
        print("⚠️ 7 days left — time to order next batch")
    elif days_remaining == 4:
        print("🚨 4 days left — order urgently")
    else:
        print(f"✅ {days_remaining} days remaining until batch ends")

    next_batch_num = int(current["serial"]) + 1
    next_batch = [e for e in entries if e["serial"] == next_batch_num + 0.1]

    if next_batch and next_batch[0]["ordered"] is None:
        print("📋 Next batch not ordered yet")


if __name__ == "__main__":
    wb = load_workbook("assets/Medicine Tracker.xlsx")
    ws = wb["Schedule Of Medication"]
    entries = get_entries(ws)
    current = find_current_entry(entries)
    check_alerts(entries, current)
