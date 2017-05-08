package ua.org.javatraining.andrii_tkachenko.view;

import org.hibernate.validator.constraints.NotEmpty;
import ua.org.javatraining.andrii_tkachenko.data.model.category.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tkaczenko on 08.05.17.
 */
public class CategoryForm {
    private int id;

    @NotEmpty
    private String name;

    private String description;
    private Category parentCategory;

    private List<Category> subCategories = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }
}
