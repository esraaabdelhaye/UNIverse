import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

interface DoctorRegistrationData {
    name: string;
    email: string;
    phoneNumber: string;
    officeLocation: string;
    title: string;
    departmentId: number | string;
    expertise: string;
    password: string;
}

interface Department {
    id: number;
    name: string;
}

@Component({
    selector: 'app-create-doctor-account',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './create-doctor-account.html',
    styleUrls: ['./create-doctor-account.css']
})
export class CreateDoctorAccount implements OnInit {
    private http = inject(HttpClient);
    private router = inject(Router);

    currentStep = 1;
    totalSteps = 3;
    isSubmitting = false;

    formData: DoctorRegistrationData = {
        name: '',
        email: '',
        phoneNumber: '',
        officeLocation: '',
        title: '',
        departmentId: '',
        expertise: '',
        password: ''
    };

    confirmPassword: string = '';
    agreeToTerms: boolean = false;
    departments: Department[] = [];

    ngOnInit(): void {
        this.loadDepartments();
    }

    loadDepartments(): void {
        this.http.get<Department[]>('http://localhost:8080/departments')
            .subscribe({
                next: (data) => {
                    this.departments = data;
                },
                error: (error) => {
                    console.error('Error loading departments:', error);
                    // Set default if fails
                    this.departments = [{ id: 1, name: 'Computer Science' }];
                }
            });
    }


    async nextStep(): Promise<void> {
        // Validate current step before moving forward
        const isValid = await this.validateCurrentStep();
        if (!isValid) {
            return;
        }
        
        if (this.currentStep < this.totalSteps) {
            this.currentStep++;
        }
    }

    prevStep(): void {
        if (this.currentStep > 1) {
            this.currentStep--;
        }
    }

    private async validateCurrentStep(): Promise<boolean> {
        if (this.currentStep === 1) {
            // Validate step 1: Personal Information
            if (!this.formData.name || !this.formData.email) {
                alert('Please fill in your name and email');
                return false;
            }
            // Basic email validation
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(this.formData.email)) {
                alert('Please enter a valid email address');
                return false;
            }
            
            // Check if email is already registered
            try {
                const isAvailable = await this.http.get<boolean>(
                    `http://localhost:8080/api/doctors/check-email/${this.formData.email}`
                ).toPromise();
                
                if (!isAvailable) {
                    alert('This email is already registered! Please use a different email address.');
                    return false;
                }
            } catch (error) {
                console.error('Error checking email:', error);
                alert('Could not verify email availability. Please try again.');
                return false;
            }
        } else if (this.currentStep === 2) {
            // Validate step 2: Professional Details
            if (!this.formData.title || !this.formData.departmentId || !this.formData.expertise) {
                alert('Please fill in all required professional details');
                return false;
            }
        }
        return true;
    }


    handleSubmit(event: Event): void {
        event.preventDefault();

        // Validation
        if (this.formData.password !== this.confirmPassword) {
            alert('Passwords do not match!');
            return;
        }

        if (!this.agreeToTerms) {
            alert('Please agree to the terms and conditions');
            return;
        }

        if (this.formData.password.length < 8) {
            alert('Password must be at least 8 characters');
            return;
        }

        this.isSubmitting = true;

        // Prepare data for backend
        const registrationData = {
            name: this.formData.name,
            email: this.formData.email,
            password: this.formData.password,
            phoneNumber: this.formData.phoneNumber,
            officeLocation: this.formData.officeLocation,
            title: this.formData.title,
            expertise: this.formData.expertise,
            departmentId: Number(this.formData.departmentId)
        };

        // Call backend API
        this.http.post('http://localhost:8080/api/doctors/register', registrationData)
            .subscribe({
                next: (response) => {
                    console.log('Registration successful:', response);
                    alert('Doctor account created successfully! You can now login.');
                    this.router.navigate(['/']);
                },
                error: (error) => {
                    console.error('Registration error:', error);
                    this.isSubmitting = false;

                    if (error.status === 409) {
                        // Email already exists
                        alert('This email is already registered! Please use a different email address.');
                    } else if (error.status === 400) {
                        alert(error.error.message || 'Invalid data provided');
                    } else {
                        alert('Registration failed. Please try again.');
                    }
                }
            });
    }
}
