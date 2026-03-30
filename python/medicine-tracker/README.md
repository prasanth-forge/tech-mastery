# Medicine Tracker

Tracks Alectinib (Alecensa) batch and box schedule from `assets/Medicine Tracker.xlsx`.

## What it does
- Identifies the current active box
- Calculates days remaining in the current batch
- Alerts when the next batch hasn't been ordered

## Setup
```bash
python3 -m venv .venv
source .venv/bin/activate
pip3 install openpyxl
```

## Run
```bash
python3 medicine_tracker.py
```

## Roadmap
- Telegram notifications
- Auto-generate FOC request letters (.docx)
- Daily cron scheduling