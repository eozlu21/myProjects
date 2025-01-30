# Telco Customer Churn Prediction

This project aims to predict customer churn in a telecom dataset using multiple machine learning models. The dataset used is `Telco-Customer-Churn.csv`. The project demonstrates data preprocessing, feature engineering, model training, and evaluation using various classification models, including logistic regression, AdaBoost, and a multi-layer perceptron (MLP) neural network.

## Project Motivation
This project was created to demonstrate the knowledge gained in **ENGR 421: Introduction to Machine Learning**, a course I completed with an **A**. It showcases the application of machine learning techniques for classification problems, particularly customer churn prediction.

## Data Preprocessing
- **Handling missing values**: The `TotalCharges` column was converted to numeric, and missing values were imputed with the median.
- **Feature encoding**: One-hot encoding was used for categorical variables, and numerical features were standardized using `StandardScaler`.
- **Feature engineering**: `PCA` was applied to reduce dimensionality while preserving 95% of variance.

## Models Implemented
1. **Logistic Regression**
   - Baseline model for comparison.
   - Achieved an accuracy of **79.13%**.

2. **AdaBoost Classifier**
   - Base learner: Decision Tree (max depth = 2)
   - Trained with 100 estimators and learning rate = 0.1.
   - Similar accuracy to logistic regression but slightly improved recall for churned customers.

3. **Multi-Layer Perceptron (MLP)**
   - Architecture:
     - 64 neurons (ReLU activation) + Dropout(0.3)
     - 32 neurons (ReLU activation) + Dropout(0.3)
     - Output layer with sigmoid activation.
   - Trained for 100 epochs with Adam optimizer.
   - Achieved an accuracy of **79.13%** with signs of slight overfitting.

## Evaluation Metrics
- **Accuracy, Precision, Recall, F1-Score** for all models.
- **ROC Curves** to compare model performance.
- **AUC Scores** to measure classification quality.

## Results Summary
- Logistic Regression provided a strong baseline with **79.13% accuracy**.
- AdaBoost performed similarly, improving recall for the churned class.
- The MLP neural network had competitive performance but showed signs of overfitting after 40 epochs.

## Conclusion
This project demonstrated multiple approaches to customer churn prediction, showing the strengths and limitations of different models. Further improvements could include hyperparameter tuning, ensemble learning, and deeper neural networks.

## Requirements
To run this project, install the following dependencies:
```sh
pip install numpy pandas matplotlib seaborn scikit-learn shap tensorflow
```

## How to Run
1. Place `Telco-Customer-Churn.csv` in the project directory.
2. Run the Python script to preprocess data and train models.
3. Review model performance and evaluation results.
4. You may adjust the hyperparameters such as the max. decision tree depth in AdaBoosting, epoch count in the MLP etc. to see how they affect the performance on the validation set.