import os

# ---------------------------- CONFIG ----------------------------

# Entities (taken from your screenshot)
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

# Output directory for repo classes
output_dir = "./repository"

# Java package name
package_name = "com.university.management.repository"

# ---------------------------------------------------------------


def generate_repo_class(entity_name):
    """Creates the Java repository class content."""
    repo_name = entity_name + "Repo"

    return f"""package {package_name};

public class {repo_name} {{

    // TODO: Implement repository logic (JPA/Hibernate/etc.)

}}
"""


def main():
    os.makedirs(output_dir, exist_ok=True)

    for entity in entities:
        repo_name = entity + "Repo"
        file_path = os.path.join(output_dir, repo_name + ".java")

        with open(file_path, "w") as f:
            f.write(generate_repo_class(entity))

        print(f"Generated: {file_path}")


if __name__ == "__main__":
    main()
