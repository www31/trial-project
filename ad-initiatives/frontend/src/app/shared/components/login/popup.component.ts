import { Component, ElementRef, ViewChild, HostListener, AfterViewInit } from '@angular/core';
import { PopupService } from './popupl.service';
import { PopupOptions } from './popup-options';
import { Observable, fromEvent, zip } from 'rxjs';

@Component({
  selector: 'app-popup',
  standalone: true,
  imports: [],
  templateUrl: './popup.component.html',
  styleUrl: './popup.component.css'
})  
export class PopupComponent implements AfterViewInit {
  @ViewChild('popup') popup!: ElementRef<HTMLDivElement>;
  @ViewChild('overlay') overlay!: ElementRef<HTMLDivElement>;
  options!: PopupOptions | undefined;
  popupAnimationEnd!: Observable<Event>;
  popupLeaveAnimation!: string;
  overlayLeaveAnimation!: string;
  overlayAnimationEnd!: Observable<Event>;
  popupLeaveTiming!: number;
  overlayLeaveTiming!: number;

  constructor(
    private popupService: PopupService,
    private element: ElementRef
  ) {}

  ngAfterViewInit(): void {
    this.options = this.popupService.options;
    this.addOptions();
    this.addEnterAnimations();
  }

  addEnterAnimations() {
    this.popup.nativeElement.style.animation =
      this.options?.animations?.popup?.enter || '';
    this.overlay.nativeElement.style.animation =
      this.options?.animations?.overlay?.enter || '';
  }

  addOptions() {
    // Style overload
    this.popup.nativeElement.style.minWidth =
      this.options?.size?.minWidth || 'auto';
    this.popup.nativeElement.style.width = this.options?.size?.width || 'auto';
    this.popup.nativeElement.style.maxWidth =
      this.options?.size?.maxWidth || 'auto';
    this.popup.nativeElement.style.minHeight =
      this.options?.size?.minHeight || 'auto';
    this.popup.nativeElement.style.height =
      this.options?.size?.height || 'auto';
    this.popup.nativeElement.style.maxHeight =
      this.options?.size?.maxHeight || 'auto';

    this.popupLeaveAnimation = this.options?.animations?.popup?.leave || '';
    this.overlayLeaveAnimation = this.options?.animations?.overlay?.leave || '';

    this.popupAnimationEnd = this.animationendEvent(this.popup.nativeElement);
    this.overlayAnimationEnd = this.animationendEvent(
      this.overlay.nativeElement
    );

    this.popupLeaveTiming = this.getAnimationTime(this.popupLeaveAnimation);
    this.overlayLeaveTiming = this.getAnimationTime(this.overlayLeaveAnimation);
  }
  
  animationendEvent(element: HTMLDivElement) {
    return fromEvent(element, 'animationend');
  }
  
  @HostListener('document:keydown.escape')
  onEscape() {
    this.popupService.close();
  }

  onClose() {
    // outside click
    this.popupService.close();
  }

  removeElementIfNoAnimation(element: HTMLDivElement, animation: string) {
    if (!animation) {
      element.remove();
    }
  }

  close() {
    this.popup.nativeElement.style.animation = this.popupLeaveAnimation;
    this.overlay.nativeElement.style.animation = this.overlayLeaveAnimation;

    // Goal here is to clean up the DOM to not have 'dead elements'
    // No animations on both elements
    if (
      !this.options?.animations?.popup?.leave &&
      !this.options?.animations?.overlay?.leave
    ) {
      this.popupService.options = undefined;
      this.element.nativeElement.remove();
      return;
    }

    // Remove element if not animated
    this.removeElementIfNoAnimation(
      this.popup.nativeElement,
      this.popupLeaveAnimation
    );
    this.removeElementIfNoAnimation(
      this.overlay.nativeElement,
      this.overlayLeaveAnimation
    );

    // Both elements are animated, remove popup as soon as longest one ends
    if (this.popupLeaveTiming > this.overlayLeaveTiming) {
      this.popupAnimationEnd.subscribe(() => {
        this.element.nativeElement.remove();
      });
    } else if (this.popupLeaveTiming < this.overlayLeaveTiming) {
      this.overlayAnimationEnd.subscribe(() => {
        this.element.nativeElement.remove();
      });
    } else {
      zip(this.popupAnimationEnd, this.overlayAnimationEnd).subscribe(() => {
        this.element.nativeElement.remove();
      });
    }

    this.popupService.options = undefined;
  }

  getAnimationTime(animation: string) {
    let animationTime = 0;
    const splittedAnimation = animation.split(' ');
    for (const expression of splittedAnimation) {
      const currentValue = +expression.replace(/s$/, '');
      if (!isNaN(currentValue)) {
        animationTime = currentValue;
        break;
      }
    }

    return animationTime;
  }
}
