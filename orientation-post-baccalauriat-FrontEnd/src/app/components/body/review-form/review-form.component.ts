import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-review-form',
  templateUrl: './review-form.component.html',
  standalone: true,
  imports: [ReactiveFormsModule, NgForOf, NgIf],
  styleUrls: ['./review-form.component.css']
})
export class ReviewFormComponent implements OnInit {
  @Input() institutionId!: number; // Receive institutionId from parent
  @Output() modalClose = new EventEmitter<void>(); // Emit event to close the modal

  reviewForm!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    // Initialize the review form
    this.reviewForm = this.fb.group({
      userName: ['', Validators.required],
      rating: [5, [Validators.required, Validators.min(1), Validators.max(5)]], // Default rating is 5
      comment: ['', Validators.required]
    });
  }

  // Submit the review form
  onSubmit(): void {
    if (this.reviewForm.valid) {
      const reviewData = {
        institutionId: this.institutionId,
        ...this.reviewForm.value
      };
      console.log('Review submitted:', reviewData);
      // Here you would call a service to send the reviewData to the server

      // Emit event to close the modal after submission
      this.modalClose.emit();
      this.reviewForm.reset(); // Reset the form
    } else {
      console.log('Review form is invalid');
    }
  }
}
