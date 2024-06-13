import { ComponentRef, EnvironmentInjector, Injectable, ViewContainerRef, TemplateRef, Type } from '@angular/core';
import { PopupComponent } from './popup.component';
import { PopupOptions } from './popup-options';

@Injectable({
  providedIn: 'root'
})
export class PopupService {
  newPopupComponent!: ComponentRef<PopupComponent>;
  options!: PopupOptions | undefined;

  constructor(
    private injector: EnvironmentInjector
  ) { }

  open(
    vcrOrComponent: ViewContainerRef,
    content: TemplateRef<Element>,
    options?: PopupOptions
  ): void;

  open<C>(
    vcrOrComponent: ViewContainerRef,
    param2?: TemplateRef<Element>,
    options?: PopupOptions
  ) {
      this.openWithTemplate(vcrOrComponent, param2 as TemplateRef<Element>);
      this.options = options;
  }

  private openWithTemplate(
    vcr: ViewContainerRef,
    content: TemplateRef<Element>
  ) {
    vcr.clear();

    const innerContent = vcr.createEmbeddedView(content);

    this.newPopupComponent = vcr.createComponent(PopupComponent, {
      environmentInjector: this.injector,
      projectableNodes: [innerContent.rootNodes],
    });
  }

  close() {
    this.newPopupComponent.instance.close();
  }
}
