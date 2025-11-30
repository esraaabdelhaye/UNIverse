import os

# ---------------------------- CONFIG ----------------------------

entities = [
    "Course",
    "Department",
    "Doctor",
    "Semester",
    "Student",
    "StudentRepresentative",
    "Supervisor",
    "TeachingAssistant"
]

# Output directory
output_dir = "./dto"

# Java package name
package_name = "com.example.backend.dto"

# ---------------------------------------------------------------


def generate_dto_class(entity_name):
    """Creates the DTO class content."""
    dto_name = entity_name + "DTO"

    return f"""package {package_name};

public class {dto_name} {{

    // TODO: Add DTO fields

}}
"""


def main():
    os.makedirs(output_dir, exist_ok=True)

    for entity in entities:
        dto_name = entity + "DTO"
        file_path = os.path.join(output_dir, dto_name + ".java")

        with open(file_path, "w") as f:
            f.write(generate_dto_class(entity))

        print(f"Generated: {file_path}")


if __name__ == "__main__":
    main()
