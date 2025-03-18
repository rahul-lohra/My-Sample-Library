import subprocess
import os

OUTPUT_FILE = "breaking_change_check.txt"

def main():
    try:
        # Get the staged changes using `git diff`
        diff_output = subprocess.check_output(["git", "diff", "--cached"]).decode("utf-8")

        # Example logic to detect breaking change:
        if "delete" in diff_output or "signature change" in diff_output:
            print("\n🚨 Potential breaking change detected!")
            result = "YES"
        else:
            result = "NO"

        # Write the result to a file
        with open(OUTPUT_FILE, "w") as f:
            f.write(result)

    except subprocess.CalledProcessError as e:
        print(f"Error getting git diff: {e}")
        with open(OUTPUT_FILE, "w") as f:
            f.write("NO")

if __name__ == "__main__":
    main()
